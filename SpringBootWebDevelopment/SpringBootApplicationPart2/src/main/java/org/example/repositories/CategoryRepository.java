package org.example.repositories;

import org.example.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select u from Category u where u.title = ?1")
    Category findByCategoryTitle(String title);
}
