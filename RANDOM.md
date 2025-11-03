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
</br> Damn how do you even fix this lol, every trick breaks the parser one way or the other. Ahh i'll just document it and tell people not to do that
</br> I actually fixed the bug, it was actually just a simple if check to see if the extracted char started with a form start lol. 
</br> Ok no that didn't work, another bug popped up, honestly I'll leave it as it was just don't put in malformed input
</br> Wait, I just added a is tracking tag cuz of a mini bug that popped up and it worked? How???? How did that fix the main bug lmao

- Ok I just got 3 tables working. They were actually easier than I expected lol, maybe I'll add more table styles later but for now I need to add table configurations 

- Ok I've added configurations to tables and markup parsing. Solid day. Im already done with this library. Maybe i'll add 2 more tables next. A customizable and a curved box draw table 
- Finally done. Interactive options? But we already have Scanner I guess depends I think its alright like this

User uploads image -> We compute a hash -> Check the db for similar or equal hashes -> we pass through an image processing tool -> we get a name or smth similar for the gif -> we pass to tenor -> we get similar images -> We compute a hash -> compare it to the original's hash