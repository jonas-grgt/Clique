# RANDOM THOUGHTS
This just contains random things I encountered while making this mini lib
- I actually spent 10 minutes wondering why my text wasn't getting styled only to discover you always have to append the ansi code before the text lol
- My proposed MD syntax.
1. Colors - red, blue, green, black, yellow, magenta, cyan, white. For bright colors, just add * to the front of the normal colors
2. Background - Just add bg_color_name and for bright background colors, add * before it i.e. *bg_red
3. Style - bold, dim, italic, ul(underline), rv(reverse video), inv or it(invisible text)
4. Reset - To reset just add [/] as the closing script
5. Not sure if this works yet but we'll see

- Haha ok I basically managed to implement my own mini DSL in an hour and a half. 
</br>I noticed that my parser could handle [[[]]] nested brackets like this so, I tried modifying the form close if statement to check if the next character in the string was an opening tag. 
</br>it worked until it didn't lol. None of my text was getting styled, but it was pretty funny I couldn't wrap my head around how it worked at first
</br> Genuinely how do you handle nested tags though?? Like its so hard keeping track of depth. Maybe a queue? A stack?

