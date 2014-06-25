package base.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Jdog653
 */
public enum Channel
{
    OOC(ChatColor.BLUE),
    SAY(ChatColor.WHITE, 8),
    YELL(ChatColor.YELLOW, 256),
    EMOTE(ChatColor.RED, 16),
    WHISPER(ChatColor.GRAY, 2);

    private final double RANGE;
    private final ChatColor COLOR;
    Channel(ChatColor c)
    {
        //-1 denotes global chat
        this(c, -1);
    }
    Channel(ChatColor c, double range)
    {
        COLOR = c;
        RANGE = range;
    }

	public void sendMessage(Player x, String message)
    {
		int recipients = 0;

		//if not emote
		if (this != EMOTE)
		{
			//if range not global
			if (this != OOC)
			{
				for (Player y : Bukkit.getOnlinePlayers())
				{
					if (y != null && y.isOnline())
					{
						if (x.getLocation().distance(y.getLocation()) <= RANGE)
						{
							y.sendMessage(getColor() + getTag() + ChatColor.GRAY + x.getName() + ": " + ChatColor.WHITE + message);
							recipients++;
						}
					}
				}
			}
			//else (if global)
			else
			{
				for (Player y : Bukkit.getOnlinePlayers())
				{
					if (y != null && y.isOnline())
					{
						y.sendMessage(getColor() + getTag() + ChatColor.GRAY + x.getName() + ": " + ChatColor.WHITE + message);
						recipients++;
					}
				}
			}
		}
		//else (if emote)
		else
		{
			for (Player y : Bukkit.getOnlinePlayers())
			{
				if (y != null && y.isOnline())
				{
					if (x.getLocation().distance(y.getLocation()) <= RANGE)
					{
						y.sendMessage(ChatColor.YELLOW + x.getName() + " " + message);
						recipients++;
					}
				}
			}
		}
		// let sender know if no one got their message
		if (recipients == 1)
        {
			x.sendMessage(ChatColor.DARK_GRAY + "No one " + (this == EMOTE ? "saw" : "heard") + " you.");
        }
    }

	public ChatColor getColor()
    {
		return COLOR;
	}

	public String getTag()
    {
		return "(" + super.toString().replace('_', ' ') + ")";
	}
}
