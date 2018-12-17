package actor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import actor.model.Actor;
import actor.repository.ActorRepository;

/**
 * The ActorController defines all the rest api for handling actors.
 */
@RestController
@RequestMapping("/api")
public class ActorController {

	@Autowired
	private ActorRepository actorRepository;
	
	/**
	 * Get all the actors.
	 * @return a list with all the actors
	 * @throws Exception 
	 */
	@RequestMapping(value = "/actors", method = RequestMethod.GET)
	public ResponseEntity<List<Actor>> getAllActors(){
		System.out.println(">> ok findAll");
		List<Actor> listActor = null;
		try {
			listActor = actorRepository.findAll();
			for (Actor actor : listActor) {
				System.out.println(actor);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(listActor);
	}
	
	/**
	 * Get a specific actor based on ID
	 * @param id : the id of actor.
	 * @return the actor
	 * @throws Exception 
	 */
	@RequestMapping(value = "/actor/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getActor(@PathVariable Long id){
		System.out.println(">> ok findOne");

		Optional<Actor> actor = null;
				
		try {
			actor =actorRepository.findById(id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		if(actor == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
		return ResponseEntity.status(HttpStatus.OK).body(actor);
	}
	
	/**
	 * Create a new actor.
	 * @param actor : the actor information.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/actor", method = RequestMethod.POST)
	public ResponseEntity<?> addActor(@RequestBody Actor actor){
		Actor resultActor = null;
		String firstName = actor.getFirstName();
		System.out.println(">> "+actor);
		
		if((firstName == null) || (firstName.isEmpty()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The firstname is not set !");
		
		String lastName = actor.getLastName();
		if((lastName == null) || (lastName.isEmpty()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The lastname is not set !");
		
		try {
			resultActor = actorRepository.save(actor);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(resultActor);
	}
	
	/**
	 * Update an existing actor.
	 * @param actor : the actor information.
	 * @param id : the id of actor.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/actor/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateActor(@RequestBody Actor actor,@PathVariable Long id) throws Exception {
		Actor result = null;
		System.out.println(">> "+actor);

		String firstName = actor.getFirstName();
		if((firstName == null) || (firstName.isEmpty()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The firstname is not set !");
		
		String lastName = actor.getLastName();
		if((lastName == null) || (lastName.isEmpty()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The lastname is not set !");
		
		try {
			result = actorRepository.save(actor); // TODO a voir...
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	/**
	 * Delete an existing actor.
	 * @param id : the id of actor.
	 * @throws Exception
	 */
	@RequestMapping(value = "/actor/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteActor(@PathVariable Long id){
		try {
		actorRepository.deleteById(id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	
}
