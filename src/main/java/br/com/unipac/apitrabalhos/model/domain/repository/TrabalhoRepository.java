package br.com.unipac.apitrabalhos.model.domain.repository;

import br.com.unipac.apitrabalhos.model.domain.Trabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabalhoRepository extends JpaRepository <Trabalho, Long>{

}
