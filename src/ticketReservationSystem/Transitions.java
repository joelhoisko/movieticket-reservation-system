package ticketReservationSystem;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;
/**
 * This is a class for frills. I think it might be easiest to just make static methods 
 * for easy styling
 * Now fadeIn and fadeOut for any Node can be called using syntax 
 * Transitions.fadeIn(node) or Transitions.fadeOut(Node)
 *   
 * @author Markus
 *
 */
public class Transitions {
	/**
	 * Fades in a node. Node must not be visible
	 * @param node 
	 */
	public static void fadeIn(Node node){
		if(!node.isVisible()){
			node.setVisible(true);
		}
		final FadeTransition fade = new FadeTransition(Duration.millis(500),node);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();

	}
	/**
	 * Fades out a node. Node should be visible
	 * @param node
	 */
	public static void fadeOut(final Node node){
		if(node.isVisible()){
			final FadeTransition fade = new FadeTransition(Duration.millis(500),node);
			fade.setFromValue(1);
			fade.setToValue(0);
			fade.setOnFinished(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent arg0) {
					node.setVisible(false);	
				}
			});
			fade.play();
		}
	}
}
