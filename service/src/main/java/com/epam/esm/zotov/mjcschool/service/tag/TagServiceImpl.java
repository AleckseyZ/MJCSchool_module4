package com.epam.esm.zotov.mjcschool.service.tag;

import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.repository.tag.TagRepository;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepo;

    @Autowired
    public TagServiceImpl(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public List<Tag> getAll() {
        return tagRepo.findAll();
    }

    @Override
    public List<Tag> getPage(int pageSize, int pageNumber) {
        return tagRepo.getPage(pageSize, pageNumber);
    }

    @Override
    public Optional<Tag> getById(long id) {
        return tagRepo.findById(id);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepo.save(tag);
    }

    @Override
    public boolean delete(long id) {
        boolean isDeleted = false;
        tagRepo.deleteById(id);
        if (getById(id).isEmpty()) {
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public Optional<Tag> findFavoriteTagOfMostSpendingUser() {
        return tagRepo.findFavoriteTagOfMostSpendingUser();
    }
}