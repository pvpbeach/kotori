package com.pvpbeach.kotori.maintenance.command

import com.pvpbeach.kotori.KotoriVelocityData
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.velocity.VelocityCommandActor
import revxrsal.commands.velocity.annotation.CommandPermission

@Command("maintenance")
@CommandPermission("kotori.command.maintenance")
object MaintenanceCommand
{
    @Subcommand("toggle")
    fun toggle(actor: VelocityCommandActor)
    {
        KotoriVelocityData.maintenanceEnabled = !KotoriVelocityData.maintenanceEnabled

        actor.reply(
            LegacyComponentSerializer
                .legacy('&')
                .deserialize(
                    "&eMaintenance has been toggled ${
                        if (KotoriVelocityData.maintenanceEnabled)
                        {
                            "on"
                        } else
                        {
                            "off"
                        }
                    }"
                )
        )
    }
}