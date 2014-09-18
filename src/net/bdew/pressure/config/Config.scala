/*
 * Copyright (c) bdew, 2013 - 2014
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * https://raw.github.com/bdew/pressure/master/MMPL-1.0.txt
 */

package net.bdew.pressure.config

import net.bdew.lib.gui.GuiHandler

object Config {
  val guiHandler = new GuiHandler

  def load() {
    Items.load()
    Blocks.load()
    Machines.load()
  }
}