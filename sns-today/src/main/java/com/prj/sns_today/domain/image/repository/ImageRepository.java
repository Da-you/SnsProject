package com.prj.sns_today.domain.image.repository;

import com.prj.sns_today.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
