/*
 * Copyright (c) bdew, 2013 - 2015
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.pressure.blocks.valves.sensor

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.bdew.lib.DecFormat
import net.bdew.lib.block.{HasTE, SimpleBlock}
import net.bdew.pressure.blocks.valves.BlockValve
import net.bdew.pressure.misc.{DataSlotFluidAverages, FluidNameHelper}
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util._
import net.minecraft.world.{IBlockAccess, World}

object BlockPipeSensor extends SimpleBlock("PipeSensor", Material.iron) with HasTE[TilePipeSensor] with BlockValve {
  override val TEClass = classOf[TilePipeSensor]

  setHardness(2)

  override def canProvidePower = true

  override def isProvidingWeakPower(w: IBlockAccess, x: Int, y: Int, z: Int, side: Int) = {
    val facing = getFacing(w, x, y, z)
    if (facing.ordinal() != side && facing.getOpposite.ordinal() != side && isPowered(w, x, y, z))
      15
    else
      0
  }

  def sendAveragesToPlayer(ds: DataSlotFluidAverages, player: EntityPlayer): Unit = {
    val flow = ds.getAverages.toList.filter(_._2 > 0.000001).sortBy(-_._2)
    if (flow.size > 0) {
      val chatYellow = new ChatStyle().setColor(EnumChatFormatting.YELLOW).setBold(true)
      player.addChatComponentMessage(new ChatComponentTranslation("pressure.message.flow.head", Integer.valueOf(ds.values.size)))
      for ((fluid, amount) <- flow) {
        player.addChatComponentMessage(
          new ChatComponentTranslation(" * %s - %s mB/t",
            new ChatComponentTranslation(FluidNameHelper.sanitizeUnlocalizedName(fluid)).setChatStyle(chatYellow),
            new ChatComponentText(DecFormat.short(amount)).setChatStyle(chatYellow)
          )
        )
      }
    }
    else {
      player.addChatComponentMessage(new ChatComponentTranslation("pressure.message.flow.empty")
        .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)))
    }
  }

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, meta: Int, xOffs: Float, yOffs: Float, zOffs: Float): Boolean = {
    if (player.isSneaking) return false
    if (world.isRemote) return true
    sendAveragesToPlayer(getTE(world, x, y, z).averages, player)
    true
  }

  @SideOnly(Side.CLIENT)
  override def registerBlockIcons(ir: IIconRegister) = {
    frontIcon = ir.registerIcon("pressure:%s/front".format(name.toLowerCase))
    sideIconOn = ir.registerIcon("pressure:%s/side_on".format(name.toLowerCase))
    sideIconOff = ir.registerIcon("pressure:%s/side_off".format(name.toLowerCase))
  }
}
