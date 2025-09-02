package ru.job4j.github.analysis.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.github.analysis.model.Commit;

import java.util.List;

@Repository
public interface CommitStore extends JpaRepository<Commit, Long> {

    List<Commit> findByRepositoryFullName(String fullName);

    boolean existsBySha(String sha);

}
