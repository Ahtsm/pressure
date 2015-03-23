/*
 * Copyright (c) bdew, 2013 - 2015
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.pressure.sensor.data

import net.bdew.pressure.sensor.{Icons, Sensors}

case class ParameterFill(uid: String, iconName: String, test: (Double, Double) => Boolean) extends Sensors.SensorParameter with Icons.Loader

object ParameterFill {
  val empty = ParameterFill("fill.empty", "fillEmpty", (n, c) => n <= 0)
  val nonEmpty = ParameterFill("fill.not.empty", "fillNotEmpty", (n, c) => n > 0)
  val gt25 = ParameterFill("fill.gt25", "fill25", _ / _ >= 0.25D)
  val gt50 = ParameterFill("fill.gt50", "fill50", _ / _ >= 0.50D)
  val gt75 = ParameterFill("fill.gt75", "fill75", _ / _ >= 0.75D)
  val full = ParameterFill("fill.full", "fillFull", _ >= _)
  val nonFull = ParameterFill("fill.not.full", "fillNotFull", _ < _)
}
