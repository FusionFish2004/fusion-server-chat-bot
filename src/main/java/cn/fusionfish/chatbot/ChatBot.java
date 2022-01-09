package cn.fusionfish.chatbot;

import cn.fusionfish.chatbot.bot.Bot;
import cn.fusionfish.chatbot.bot.BotThread;
import cn.fusionfish.core.plugin.FusionPlugin;

public class ChatBot extends FusionPlugin {

    private static Bot bot;

    @Override
    protected void enable() {
        BotThread thread = new BotThread();
        thread.start();
        //启动机器人线程
        bot = thread.getBot();
    }

    @Override
    protected void disable() {
        bot.getBot().close();
    }

    public static Bot getBot() {
        return bot;
    }

}
