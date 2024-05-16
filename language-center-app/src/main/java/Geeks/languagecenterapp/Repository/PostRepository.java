package Geeks.languagecenterapp.Repository;

import Geeks.languagecenterapp.Model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Integer> {
}
