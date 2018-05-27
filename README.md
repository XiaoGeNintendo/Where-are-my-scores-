# Where are my scores?
A music game about ZKY's score!

Much like _Osu_ 's _Catch the beat_ mode.

# How to play

Install your beatmaps at "songs" folder and open the application to run.

Then follow the ingame instuction to start.

Move your mouse to catch the items dropping from above.

Your task is to miss the beat as few as possible

After one round, your mark and score will be shown to you and you'll be given your "rank".The rank will be one of {1st,100th,200th,300th,400th,500th,600th,last}. Better rank means better levels you have reached.

The maximum score will be shown on the music choosing page

# How to make beatmaps
First open your songs folder and create a sub-folder. Then write a file called "beatmap.json" with the following format:
```java
{
    "dropTime":500, //The time that a note takes to drop from the top to the bottom
    "notes":[[1.059,300]], //The notes array. Every element of the array should be a two-element array [e1,e2]. e1 is a float means the time that the note appears and e2 is an integer means the x position of the note that will appear.
    "bgPath":"", //Background Image Path. Empty for no image
    "notePath":"", //Note Image Path. Empty for default
    "catcherPath":"", //Catcher's Image Path. Empty for default
    "songPath":"Black Board.mp3", //Song path. DON'T LEAVE IT EMPTY
    "l1":0, //What score at least does a player need if he wants to get the 1st rank
    "l100":0, //What score at least does a player need if he wants to get the 100th rank
    "l200":0, //the same
    "l300":0, //the same
    "l400":0, //the same
    "l500":0, //the same
    "l600":0, //the same
    "last":0, //the same
    "r":1, //the UI font color during playing. from 0~1, a float number
    "g":0, //the UI font color during playing. from 0~1, a float number
    "b":0, //the UI font color during playing. from 0~1, a float number
    "a":1 //the UI font color during playing. from 0~1, a float number
}
```

The order doesn't matter and adding some other stuffs are allowed. But **don't miss anything! Or may cause a game crash!**
you can find a full example in the folder songs/ExampleSong on Github

then copy the assets into the folder. And if everything's ready, open the application and play!

# Dealing with crashes
Oops! My application crashes! What should I do?? 

First, think about what you did. Then give me an issue on Github. If you uses cmd/shell to open the game, tell me what exactly exception has been throwed.

# Platforms
Work on Windowsx64. idk for others... **But doesn't work on phones 100%.**

# Other
Well... Made by LibGdx and i am just a newbie... So if you find somewhere may cause a memory leak or other stuff, tell me plz.