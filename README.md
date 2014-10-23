# Fancy Text Scroll
===================

## Description
This program creates a special area where predefined text is shown while scrolling from bottom to top.

## Why bother creating something like this?
I created this in December 2004. Back then, while surfing the net, I found a site where they sold applets and showed what they could do; one of them was displaying text going from side to side, from top to bottom, and when clicked, it jumped to a designated URL. I thought it was cool, but instead of paying just to use it (it was closed source), I took it as a challenge and tried to achieve the same.

It took me a while, but when I was done, I used the applet in a couple of sites where I was a collaborator, and surpisingly there were people interested on using it too. I thought the code was ugly, and it was not user-friendly, so I didn't provided it to anyone. However, I realized that if someone was ever to use it, there should be a way to create the text to show in the format the program expected, so I created a simple Swing application that did the trick (it is included here).

In short: It was for fun and for the challenge.

## Classes
- News: Contains the title, contents, url and target (as in the <a> HTML tag) of a news (text) to show, as well as the following features for title and contents:

   Font
   Color
   Highlight Color

- TextLine: A line that will be displayed. TextLines are created from News title and contents, and the contained text and its position depends on the number of characters, font size, width and height of the display area.

- TextDisplayArea: An applet that creates the area where the scrolling text will be displayed, and also creates the text to display based either on the given parameters or from a text file (in a special format).

- FillAppletInfo: Swing application that eases the creation of content to display in the applet. It asks for a file name when starting, and fills it with text the user adds.

## How to run

### Application to create text to show
Simply run the FillAppletInfo class. When starting, choose a location and provide a file name to save the text.

A window will appear. In it, you can input text in form of "title" and "content", and you can select font type, size, normal and highlight color for each element. Optionally, you can provide a link to jump when clicking the text, and whether the link should be open in the same window or in a new one.

### Applet
The usual way to run applets. The recommended way to is by using the *appletviewer* command; it comes with the JDK. You will need to create a test page, add an <applet> tag and provide the necessary parameters:

- bgcolor: Background color (in RGB form).
- bgimage: Background image full path. Overrides color.
- topmargin in pixels.
- scroll: Scroll speed, in seconds.
- padding in pixels.
- file: Full path to the file containing the text to show.
- dir: Full path to a directory containing files in the format expected by the "file" parameter.
- pause: Number of seconds each text scroll will pause when reaching the top.

## Disclaimer
As something I created for fun long time ago, comments in the code are scarce.
