//AudioFilePlayer.java
/*Code researched and written by John Brosnan who sent it on for use with the audio aspects of the project
  It uses some of the JavaFX classes and contains its own driver to show how it might be used. The GUI contains
  2 JButtons, one plays an audio file while the other generates a random number between 1 and 1000*/

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.embed.swing.JFXPanel;
import javafx.application.Platform;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// AudioPlayer code courtesy of John Brosnan.
// File trimmed down to necessary sections.


       
public class AudioFilePlayer extends JFrame{
	
	JButton audioButton; //need this global for event-handling later

	static MediaPlayer mediaPlayer; //need to declare this reference globally to prevent garbage collector
									//from removing it prematurely when play() is called in the playAudio()
									//method, which would stop playing the clip after a few seconds
       
    
    public static void playAudio(String path)
    {			
		//create a new Media object using the file path specified in the call to playAudio
		//this will become the audio clip object we wish to play
		
		Media audioClip = new Media(new File(path).toURI().toString());
		
		//create a new MediaPlayer object that will be used to play the audio clip
		
		mediaPlayer = new MediaPlayer(audioClip);
		
		/*when the clip has played to completion, the code below ensures that the JavaFX thread will terminate
		 I have commented it out though because you generally want a JavaFX thread to keep operating
		 after an audio file has been fully played, but a JavaFX thread won't terminate automatically
	     without a call to Platform.exit() or System.exit(). So, if you run this program with the code below commented out, 
		 the JavaFX thread will continue after the audio clip has played meaning you can press the button again and again
		 to replay the audio - if you uncomment it, exceptions are thrown after the first few presses because the
		 audio requires JavaFX libraries which are no longer available as the thread they run on is terminated
		 The other button, for random number generation, based on the swing event-dispatch thread, continues to operate 
		 as normal though, even with exceptions being thrown for the other thread*/
		
		/*mediaPlayer.setOnEndOfMedia(new Runnable() {
 			 public void run() {
		    	Platform.exit();
		  	}
		});*/
			
		/*now actually play the audio clip - some exception-handling code to attempt to play the .wav audio clip associated with audioFile1. 
		 *If it succeeds, you will hear the sound of a gunshot, otherwise it fails either because the audio clip could not be found, or the 
		 *MediaPlayer object had some difficulty in actually playing the file. If it fails, the "catch" clause executes and an error message
		 *is displayed to the console*/		
		
		try
		{
			mediaPlayer.play();
		}
		catch(Exception e)
    	{
    		System.out.println("The audio file " + path + " could not be played!");
    	}
			     
    }

}