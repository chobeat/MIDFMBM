package org.anacletogames.maps.assets

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile

/**
  * Created by lara on 10/30/16.
  */
class Tile9Region(texture: Texture, x: Int, y: Int, size: Int) {
  lazy val NW = new TextureRegion(texture, x, y, size, size)
  lazy val N = new TextureRegion(texture, x + size, y, size, size)
  lazy val NE = new TextureRegion(texture, x + 2 * size, y, size, size)
  lazy val Center = new TextureRegion(texture, x + size, y + size, size, size)
  lazy val E = new TextureRegion(texture, x + 2 * size, y + size, size, size)
  lazy val W = new TextureRegion(texture, x, y + size, size, size)
  lazy val SW = new TextureRegion(texture, x, y + 2 * size, size, size)
  lazy val S = new TextureRegion(texture, x + size, y + 2 * size, size, size)
  lazy val SE =
    new TextureRegion(texture, x + 2 * size, y + 2 * size, size, size)

  lazy val NWCell = new Cell()
  NWCell.setTile(new StaticTiledMapTile(NW))

  lazy val NCell = new Cell()
  NCell.setTile(new StaticTiledMapTile(N))
  lazy val NECell = new Cell()
  NECell.setTile(new StaticTiledMapTile(NE))
  lazy val WCell = new Cell()
  WCell.setTile(new StaticTiledMapTile(W))
  lazy val CenterCell = new Cell()
  CenterCell.setTile(new StaticTiledMapTile(Center))
  lazy val ECell = new Cell()
  ECell.setTile(new StaticTiledMapTile(E))
  lazy val SWCell = new Cell()
  SWCell.setTile(new StaticTiledMapTile(SW))
  lazy val SECell = new Cell()
  SECell.setTile(new StaticTiledMapTile(SE))

  lazy val SCell = new Cell()
  SCell.setTile(new StaticTiledMapTile(S))

}
