# teamquiz
A program to randomize the order of pictures and the alternatives for a Listerine team quiz.

## Usage
To use the program:
1. Open a terminal window
2. Download the code with the command
`git clone https://github.com/malinkallen/teamquiz.git`
3. Step into the teamquiz directory: `cd teamquiz/`
4. Make sure that the file `teamquiz.input` contains three lines starting with
the keyword `providers`, `pictures` and `alternatives` respectively. Each
keyword should be followed by a `=`, and
    * a comma separated list of those who contributed with pictures on the line
	starting with `providers`
    * a comma separated list of identifiers for the pictures (might be
	`picture1`, `picture2` and so on, or something more descriptive) on the line
	starting with `pictures`
    * a comma separated list of persons to use as alternatives on the line
	starting with `alternatives`. This may be the same list as on the
	`providers` line, or you may add some people to trick the participants.
5. Run the program: `java TeamQuiz`

For an example of what `teamquiz.intput` might look like, see the current
version of the file.

The program will print the pictures (as all combinations of providers and
pictures) in random order. Below each picture, 4 alternatives that can be used
are provided in random order.
