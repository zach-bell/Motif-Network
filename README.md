# Motif-Network
<p>This is a program designed to sort through a text file with strings of characters to determine the distance and matching score of all elements in the file.
The program will take in a file destination as the <strong>input</strong>, the file output destination as <strong>output</strong>,
and the number of processing threads to use as <strong>cores</strong> (it is suggested to have it at 4).</p>

# Usage of runable jar
<p>java -jar MotifSearching <strong>input</strong> <strong>output</strong> <strong>cores</strong><br><strong>cores</strong></p>
<p><strong>Input</strong> and <strong>Output</strong> is needed, but <strong>cores</strong> is an optional input. If no value is found for 
	<strong>cores</strong> it will default to 4.</p>

# How to install into eclipse
<ol>
<li><p>First clone the project or download as a zip.</p></li>

<li><p>Unpack the archived projects.</p></li>

<li><p>Import an existing project into eclipse.</p>
	<img src="https://i.gyazo.com/acddb3bfee3076def77af128685c6818.png"></img>
	<img src="https://i.gyazo.com/dbc6dbdc5707411c981c4a8831eae494.png"></img></li>

<li><p>Browse for the main project folder and <strong>only select MotifSearching</strong> for the project.
	Make sure you <strong>uncheck motif.mapreducer</strong> as that is not used. Not doing this will cause some file read conflicts unless 
	you make some changes yourself in the <strong>MotifLauncher</strong> class.</p>
	<img src="https://i.gyazo.com/dbc6dbdc5707411c981c4a8831eae494.png"></img><p>You should have a structure looking like this.</p>
	<img src="https://i.gyazo.com/32968f2c1266cf1408f9c48747dea6ad.png"></img></li>

<li><p>Make sure you find the launcher class <strong>MotifLauncher</strong> to run the project from.</p>
	<img src="https://i.gyazo.com/679f5ec1ab132ec2c1113cf73ab7ccef.png"></img></li>
</ol>
