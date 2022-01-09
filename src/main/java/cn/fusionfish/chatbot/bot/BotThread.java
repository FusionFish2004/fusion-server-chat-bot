package cn.fusionfish.chatbot.bot;

public class BotThread extends Thread {

    private Bot bot;

    @Override
    public void run() {
        this.bot = new Bot();
    }

    public Bot getBot() {
        return bot;
    }
}
