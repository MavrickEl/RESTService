package service;

import dto.AuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO save(AuthorDTO authorDTO);
    AuthorDTO findById(Long id);
    List<AuthorDTO> findAll();
    void delete(Long id);
}
