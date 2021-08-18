public class jojoBotMain {
	public static void main(String[]args) throws Exception{
		jojoBot bot = new jojoBot();
		
		bot.setVerbose(true);
		bot.connect("irc.freenode.net");		
	    bot.joinChannel("#jojoBot");

	}

}
