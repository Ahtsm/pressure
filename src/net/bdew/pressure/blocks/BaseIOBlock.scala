/*
 * Copyright (c) bdew, 2013 - 2014
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.pressure.blocks

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.bdew.lib.block.HasTE
import net.bdew.lib.rotate.{BaseRotatableBlock, IconType}
import net.bdew.pressure.render.RotatedBlockRenderer
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection

class BaseIOBlock[T <: TileFilterable](name: String, teClass: Class[T])
  extends Block(Material.iron) with BaseRotatableBlock with HasTE[T] with BlockFilterable[T] {
  override val TEClass = teClass

  setBlockName("pressure." + name)
  setHardness(2)

  override def getFacing(world: IBlockAccess, x: Int, y: Int, z: Int) =
    ForgeDirection.values()(world.getBlockMetadata(x, y, z))

  override def setFacing(world: World, x: Int, y: Int, z: Int, facing: ForgeDirection) =
    world.setBlockMetadataWithNotify(x, y, z, facing.ordinal(), 3)

  override def rotateBlock(world: World, x: Int, y: Int, z: Int, axis: ForgeDirection) = {
    world.setBlockMetadataWithNotify(x, y, z, (world.getBlockMetadata(x, y, z) + 1) % 6, 3)
    true
  }

  @SideOnly(Side.CLIENT)
  override def getRenderType = RotatedBlockRenderer.id

  @SideOnly(Side.CLIENT)
  override def getIcon(meta: Int, kind: IconType.Value) = kind match {
    case IconType.BACK => backIcon
    case IconType.FRONT => frontIcon
    case _ => sideIcon
  }

  var frontIcon, sideIcon, backIcon: IIcon = null
  @SideOnly(Side.CLIENT)
  override def registerBlockIcons(ir: IIconRegister) = {
    frontIcon = ir.registerIcon("pressure:%s/front".format(name))
    backIcon = ir.registerIcon("pressure:%s/back".format(name))
    sideIcon = ir.registerIcon("pressure:%s/side".format(name))
  }
}
