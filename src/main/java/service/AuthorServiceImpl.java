package service;

import dto.AuthorDTO;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorService authorService;

    public AuthorServiceImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public AuthorDTO save(AuthorDTO authorDTO) {
        return null;
    }

    @Override
    public AuthorDTO findById(Long id) {
        return null;
    }

    @Override
    public List<AuthorDTO> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
