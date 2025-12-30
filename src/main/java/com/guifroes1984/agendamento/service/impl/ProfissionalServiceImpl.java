package com.guifroes1984.agendamento.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guifroes1984.agendamento.dto.request.ProfissionalRequestDTO;
import com.guifroes1984.agendamento.dto.response.ProfissionalResponseDTO;
import com.guifroes1984.agendamento.dto.response.ServicoSimplesResponseDTO;
import com.guifroes1984.agendamento.exception.BusinessException;
import com.guifroes1984.agendamento.model.Profissional;
import com.guifroes1984.agendamento.model.Servico;
import com.guifroes1984.agendamento.repository.ProfissionalRepository;
import com.guifroes1984.agendamento.repository.ServicoRepository;
import com.guifroes1984.agendamento.service.ProfissionalService;

import jakarta.transaction.Transactional;

@Service
public class ProfissionalServiceImpl implements ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Override
    @Transactional
    public ProfissionalResponseDTO criar(ProfissionalRequestDTO profissionalDTO) {
        validarProfissionalAntesDeCriar(profissionalDTO);
        
        Profissional profissional = new Profissional();
        profissional.setNome(profissionalDTO.getNome());
        profissional.setTelefone(profissionalDTO.getTelefone());
        profissional.setEmail(profissionalDTO.getEmail());
        profissional.setHoraInicio(profissionalDTO.getHoraInicio());
        profissional.setHoraFim(profissionalDTO.getHoraFim());
        profissional.setAtivo(profissionalDTO.getAtivo() != null ? profissionalDTO.getAtivo() : true);

        Set<Servico> especialidades = new HashSet<>();
        for (Long servicoId : profissionalDTO.getEspecialidadesIds()) {
            Servico servico = servicoRepository.findById(servicoId)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + servicoId));
            if (!servico.getAtivo()) {
                throw new BusinessException("Serviço ID " + servicoId + " está inativo");
            }
            especialidades.add(servico);
        }
        profissional.setEspecialidades(especialidades);

        profissional.setDiasDisponiveis(profissionalDTO.getDiasDisponiveis());
        
        Profissional profissionalSalvo = profissionalRepository.save(profissional);
        return toResponseDTO(profissionalSalvo);
    }

    @Override
    public ProfissionalResponseDTO buscarPorId(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + id));
        return toResponseDTO(profissional);
    }

    @Override
    public ProfissionalResponseDTO buscarPorEmail(String email) {
        Profissional profissional = profissionalRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com email: " + email));
        return toResponseDTO(profissional);
    }

    @Override
    public List<ProfissionalResponseDTO> listarTodos() {
        return profissionalRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfissionalResponseDTO> listarAtivos() {
        return profissionalRepository.findByAtivoTrue().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfissionalResponseDTO> buscarPorNome(String nome) {
        return profissionalRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfissionalResponseDTO> buscarPorTermo(String termo) {
        return profissionalRepository.buscarAtivosPorTermo(termo).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfissionalResponseDTO> buscarPorServico(Long servicoId) {
        return profissionalRepository.buscarPorServico(servicoId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProfissionalResponseDTO atualizar(Long id, ProfissionalRequestDTO profissionalDTO) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + id));
        
        validarProfissionalAntesDeAtualizar(profissional, profissionalDTO);
        
        profissional.setNome(profissionalDTO.getNome());
        profissional.setTelefone(profissionalDTO.getTelefone());
        profissional.setEmail(profissionalDTO.getEmail());
        profissional.setHoraInicio(profissionalDTO.getHoraInicio());
        profissional.setHoraFim(profissionalDTO.getHoraFim());
        
        if (profissionalDTO.getAtivo() != null) {
            profissional.setAtivo(profissionalDTO.getAtivo());
        }

        Set<Servico> especialidades = new HashSet<>();
        for (Long servicoId : profissionalDTO.getEspecialidadesIds()) {
            Servico servico = servicoRepository.findById(servicoId)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + servicoId));
            if (!servico.getAtivo()) {
                throw new BusinessException("Serviço ID " + servicoId + " está inativo");
            }
            especialidades.add(servico);
        }
        profissional.setEspecialidades(especialidades);

        profissional.setDiasDisponiveis(profissionalDTO.getDiasDisponiveis());
        
        Profissional profissionalAtualizado = profissionalRepository.save(profissional);
        return toResponseDTO(profissionalAtualizado);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + id));
        
        // Verificar se o profissional tem agendamentos futuros
        // (Esta verificação será implementada no Commit 4)
        
        profissionalRepository.delete(profissional);
    }

    @Override
    @Transactional
    public ProfissionalResponseDTO ativar(Long id) {
        return alterarStatus(id, true);
    }

    @Override
    @Transactional
    public ProfissionalResponseDTO desativar(Long id) {
        return alterarStatus(id, false);
    }

    @Override
    public boolean existePorEmail(String email) {
        return profissionalRepository.existsByEmail(email);
    }

    @Override
    public boolean existePorTelefone(String telefone) {
        return profissionalRepository.existsByTelefone(telefone);
    }

    private ProfissionalResponseDTO alterarStatus(Long id, boolean ativo) {
        Profissional profissional = profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + id));
        
        profissional.setAtivo(ativo);
        profissional.setDataAtualizacao(java.time.LocalDateTime.now());
        
        Profissional profissionalAtualizado = profissionalRepository.save(profissional);
        return toResponseDTO(profissionalAtualizado);
    }

    private void validarProfissionalAntesDeCriar(ProfissionalRequestDTO profissionalDTO) {
        if (profissionalRepository.existsByEmail(profissionalDTO.getEmail())) {
            throw new BusinessException("Já existe um profissional cadastrado com este email");
        }
        
        if (profissionalRepository.existsByTelefone(profissionalDTO.getTelefone())) {
            throw new BusinessException("Já existe um profissional cadastrado com este telefone");
        }
        
        if (profissionalDTO.getHoraInicio().isAfter(profissionalDTO.getHoraFim())) {
            throw new BusinessException("Horário de início não pode ser após o horário de fim");
        }
        
        if (profissionalDTO.getEspecialidadesIds() == null || profissionalDTO.getEspecialidadesIds().isEmpty()) {
            throw new BusinessException("Pelo menos uma especialidade deve ser informada");
        }
        
        if (profissionalDTO.getDiasDisponiveis() == null || profissionalDTO.getDiasDisponiveis().isEmpty()) {
            throw new BusinessException("Pelo menos um dia disponível deve ser informado");
        }
        
        validarDiasDisponiveis(profissionalDTO.getDiasDisponiveis());
    }

    private void validarProfissionalAntesDeAtualizar(Profissional profissional, ProfissionalRequestDTO profissionalDTO) {
        if (!profissional.getEmail().equals(profissionalDTO.getEmail()) && 
            profissionalRepository.existsByEmailAndIdNot(profissionalDTO.getEmail(), profissional.getId())) {
            throw new BusinessException("Já existe um profissional cadastrado com este email");
        }
        
        if (!profissional.getTelefone().equals(profissionalDTO.getTelefone()) && 
            profissionalRepository.existsByTelefoneAndIdNot(profissionalDTO.getTelefone(), profissional.getId())) {
            throw new BusinessException("Já existe um profissional cadastrado com este telefone");
        }
        
        if (profissionalDTO.getHoraInicio().isAfter(profissionalDTO.getHoraFim())) {
            throw new BusinessException("Horário de início não pode ser após o horário de fim");
        }
        
        if (profissionalDTO.getEspecialidadesIds() == null || profissionalDTO.getEspecialidadesIds().isEmpty()) {
            throw new BusinessException("Pelo menos uma especialidade deve ser informada");
        }
        
        if (profissionalDTO.getDiasDisponiveis() == null || profissionalDTO.getDiasDisponiveis().isEmpty()) {
            throw new BusinessException("Pelo menos um dia disponível deve ser informado");
        }
        
        validarDiasDisponiveis(profissionalDTO.getDiasDisponiveis());
    }

    private void validarDiasDisponiveis(Set<Integer> diasDisponiveis) {
        for (Integer dia : diasDisponiveis) {
            if (dia < 1 || dia > 7) {
                throw new BusinessException("Código de dia inválido: " + dia + ". Deve ser entre 1 e 7");
            }
        }
    }

    private ProfissionalResponseDTO toResponseDTO(Profissional profissional) {
        Set<ServicoSimplesResponseDTO> especialidadesDTO = profissional.getEspecialidades().stream()
                .map(servico -> new ServicoSimplesResponseDTO(
                        servico.getId(),
                        servico.getNome(),
                        servico.getDescricao(),
                        servico.getDuracaoMinutos(),
                        servico.getPreco()
                ))
                .collect(Collectors.toSet());
        
        return new ProfissionalResponseDTO(
                profissional.getId(),
                profissional.getNome(),
                profissional.getTelefone(),
                profissional.getEmail(),
                profissional.getHoraInicio(),
                profissional.getHoraFim(),
                profissional.getAtivo(),
                profissional.getDataCadastro(),
                profissional.getDataAtualizacao(),
                especialidadesDTO,
                profissional.getDiasDisponiveis()
        );
    }
}
