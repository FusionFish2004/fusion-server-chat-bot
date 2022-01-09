package cn.fusionfish.chatbot.bot;

import cn.fusionfish.chatbot.ChatBot;
import cn.fusionfish.core.utils.ConsoleUtil;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.utils.BotConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import static cn.fusionfish.core.utils.ConsoleUtil.*;

import java.util.List;
import java.util.stream.Collectors;

public class Bot {

    private final FileConfiguration config = ChatBot.getInstance().getConfig();
    private final long qq = config.getLong("bot.qq");
    private final String password = config.getString("bot.password", "");

    private final net.mamoe.mirai.Bot bot = BotFactory.INSTANCE.newBot(qq,password, new BotConfiguration(){{
        setProtocol(MiraiProtocol.ANDROID_PHONE);
        setWorkingDir(ChatBot.getInstance().getDataFolder());
        fileBasedDeviceInfo("myDeviceInfo.json");
        noNetworkLog();
        noBotLog();
    }});

    private final Group group;

    public Bot() {

        info("Logging in...");
        info(" - id: " + qq);
        info(" - password:" + password);

        bot.login();

        long groupId = config.getLong("bot.group");
        this.group = bot.getGroup(groupId);

        long admin = config.getLong("bot.admin");
        Friend friend = bot.getFriend(admin);
        assert friend != null;
        friend.sendMessage("ChatBot successfully deployed.");
    }

    public void sendGroupMessage(String string) {
        group.sendMessage(string);
        String msg = "[Group]Bot: " + string;
        info(msg);
    }

    public void sendChatMessage(long id, String string) {
        NormalMember member = group.get(id);
        if (member == null) {
            return;
        }
        String name = member.getNick();
        String msg = "[Member]To " + name + "(" + id + "): " + string;
        member.sendMessage(string);
        info(msg);
    }

    public net.mamoe.mirai.Bot getBot() {
        return bot;
    }

    public boolean isInGroup(long id) {
        return group.get(id) != null;
    }

    public List<Long> getMembers() {
        return group.getMembers()
                .stream()
                .map(NormalMember::getId)
                .collect(Collectors.toList());
    }

}
