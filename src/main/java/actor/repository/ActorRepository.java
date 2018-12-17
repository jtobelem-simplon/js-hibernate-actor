package actor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import actor.model.Actor;


public interface ActorRepository extends JpaRepository<Actor, Long> {

}
