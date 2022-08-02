package com.pvpbeach.kotori.vpn.command

import com.pvpbeach.kotori.KotoriVelocityData

import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Default
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.command.CommandActor
import revxrsal.commands.exception.CommandErrorException
import revxrsal.commands.help.CommandHelp
import revxrsal.commands.velocity.VelocityCommandActor
import revxrsal.commands.velocity.annotation.CommandPermission
import java.util.*

@Command("vpn")
@CommandPermission("kotori.command.vpn")
object VirtualPrivateNetworkCommand
{
    private val IP_REGEX = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\$\n".toRegex()
    private val UUID_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}".toRegex()

    @Default
    fun vpn(sender: CommandActor, helpEntries: CommandHelp<String>, @Default("1") page: Int)
    {
        for (entry in helpEntries.paginate(page, 7))
        {
            sender.reply(entry)
        }
    }

    @Subcommand("whitelist")
    fun whitelist(sender: VelocityCommandActor, whitelist: String)
    {
        when
        {
            whitelist.matches(IP_REGEX) -> KotoriVelocityData.whitelistedIps.add(whitelist)
            whitelist.matches(UUID_REGEX) -> KotoriVelocityData.whitelistedIds.add(UUID.fromString(whitelist))
            else -> throw CommandErrorException("String must either be an IP-V4 address or a UUID.")
        }

        sender.reply(
            "Added to the whitelist."
        )
    }
}