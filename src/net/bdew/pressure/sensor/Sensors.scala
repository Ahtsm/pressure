/*
 * Copyright (c) bdew, 2013 - 2015
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.pressure.sensor

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.bdew.lib.sensors.RedstoneSensors
import net.bdew.pressure.blocks.tank.controller.TileTankController
import net.bdew.pressure.sensor.data.SensorTank
import net.minecraft.tileentity.TileEntity

object Sensors extends RedstoneSensors[TileEntity] {
  @SideOnly(Side.CLIENT)
  override def disabledTexture = Icons.disabled
  override def localizationPrefix = "pressure.sensor"

  val tankSensors = List(
    DisabledSensor,
    SensorTank[TileTankController]("tank", "tank", _.tank)
  )
}