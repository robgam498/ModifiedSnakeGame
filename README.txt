=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: 11906606
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

GAME:

"Territory"


BACKSTORY

OH NO! The evil cats have stolen your bone collection! You must retrieve the bones that the cat has stolen from
you by any means possible.


HOW TO PLAY

Have Java installed on your computer and launch the game.

Use the arrow keys (up, down, left, right) to move your player (dog) around the board to reach the cat, who will be
randomly generated around the 600x600 board. But, be sure not to leave your territory (bounds of the screen) or else
the game will end. Retrieve as many bones as possible before the timer runs out!

If you need to pause the game for any reason, be sure to press 'p' to pause and 'r' to resume; the current state
of the game will be saved. Also, in a separate "gamestate.txt" file your exact progress will be saved in case you
need to exit, that way you can pick up right where you left off!

If you want to restart the game, just press 'space' at the "GAME OVER" screen or anytime while playing, and you
will be able to start it again.


GAME FUNCTION

The leader cat (first cat) will be deceptive and try to hide the bone from you to make you think it hasn't been stolen,
but after you catch the leader, you will then be able to see the cats holding onto the bones behind them. Each time
you catch a cat, it will drop the bone on the ground and you will have +1 to your "Bones Retrieved" score. When you
collect 10 bones, you will receive a POWER-UP and grow in size by a factor of 2! Be sure to watch out for the edges
when you grow in size.

Your past movements are tracked every time you hit an arrow key to move around, this is show by the red dots you will
see on your screen, creating a sort of trail behind the dog, so that you can see where you have moved before.


GOOD LUCK!


===================
=: Core Concepts :=
===================

  1.2D Arrays
    In this game, a 2D array called pathArray is used to store information about the movement of the dog.
    The pathArray[doggoX][doggoY] element is set to 1 and when the dog moves to a new position on the game board,
    it will be recorded as to allow for a path to follow the dog. This is used in the game with the KeyAapter to show
    the player where they have made movements on the board to create a sort of territorial trail for the dog.

  2. Java Collections
    To make my game I used the ArrayList class from the java.util package to make an array list of Points that is
    called boneList. The ArrayList class is from Java Collections and acts like a resizable array. In my game, I use
    it to store the coordinates of the bones that the doggo collected, and it is used to keep track of where the
    overlap between the dog and cat occurs so that a bone will stay in place of the cat after the dog catches it.

  3. File/IO
    File/IO is used to save and load the game state using a pause and resume function. The game state is saved to a
    text file called "gamestate.txt" which uses a PrintWriter object. The file contains the coordinates of the dog,
    the cat, and the bones in the game (all the main components). Once the game is resumed after an exit or pause, a
    Scanner object is used to read in the coordinates from the "gamestate.txt" file and use them to resume the game
    state for the players to continue where they left off. This allows the players to leave the game and return later
    at their convenience.

  4. Testable Components
    I will test several instances that the user may come across during the game:
        1. Pause/Resume function
        2. Time remaining functions properly (counts downward from 1000 ms)
        3. bonesAdded works properly as for the boneCollected function to work
        4. Test that images are loaded in properly from files
        5. Ensure that the timer ends (does not go below 0)
        6. Test that the restart functionality works properly (spacebar)
        7. moveDog method works
        8. Test edge cases (dog collides with wall)


=========================
=: Your Implementation :=
=========================

  1. Classes and Interfaces:

    The class RunSnake is acting as an extension of JPanel, in that it is inheriting all properties and methods of
    the JPanel class. By extending JPanel, the RunSnake class can be added to a GUI and is able to be displayed on
    the screen.

    The class is also implementing the ActionListener and Runnable interfaces. ActionListener is an interface that
    allows the class to respond to user key-press interactions, to allow for movement, pause, resume, and restart.
    By implementing this interface, the  class can define its own behavior when certain events occur.

    Runnable is an interface that allows the class to be run as a separate thread. By implementing this interface,
    the class can run concurrently with other parts of the game.


  2. Stumbling Blocks

    I struggled with the random generation of the cat within the bounds that I wanted because it wasn't supposed
    to generate in a spot that was unreachable by the dog. It couldn't just be the size of the screen because the
    dog wouldnt be able to intercept it at certain points due to the restriction of not being able to touch the
    border of the screen.

    I also found it difficult to track where the cat was and make it so that the bone would be dropped in the exact
    location that the dog intercepts the cat after the first "hidden bone" case. I also needed to update the points of
    origin for each model that way the bone would be visible behind the cat, since they are the same size images
    (10 x 10 pixels).


  3.

    I believe there is good separation and functionality in my design. The class of "RunSnake" is responsible for
    all of the game actions and displaying the game on the window. The "gamestate.txt" file saves and loads the game's
    state to act like a pause/resume feature. Furthermore, the ArrayList for storing bones and using different
    variables to track the actions of the cat and dog and their respective movements helps separate from different
    elements and methods in the game.

    The private state is well encapsulated as all the game variables are declared private and only accessible through
    the class methods. This acts as a vehicle to ensure that the game can only be modified through the classes
    intended behavior; similar to the functionality we see in Ocaml.

    The one thing I would add would be to add more comments so that users can understand more clearly what each part
    of my code does. For example, "locateCat" randomly generates the position of the cat, which makes sense to me for
    reasons of similarity in my use of locate for randomized variables that I have done in the HWs throughout the
    semester. However, I realize that it is objectively less clear for those who don't have the same thought
    process or experiences as me.


========================
=: External Resources :=
========================

  HW8: TwitterBot
  Use: To help me implement and construct IO for the pause/resume/exit feature.


  Tutorial for snake: https://zetcode.com/all/#java
  Use: To help me understand and construct my base game design.


  Images:
    Bone: https://www.gettyimages.in/photos/dog-bone
    Cat: https://www.wallpaperflare.com/search?wallpaper=pixel+cat
    Dog: https://wallpapercrafter.com/126597-undertale-dog-pixel-art-pixels-digital-art-
         motivational-black-background-ropes-video-games.html
  Use: create images for the dog, cat, and bone




