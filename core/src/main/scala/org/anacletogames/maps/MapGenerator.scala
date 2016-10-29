package org.anacletogames.maps

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.{OrthographicCamera, Texture}
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile
import com.badlogic.gdx.maps.tiled.{TiledMap, TiledMapTileLayer, TiledMapTileSet}

/**
  * Created by lara on 10/29/16.
  */
object MapGenerator {

  def generateRandomMap(width:Int, height:Int): TiledMap={
    val genMap=new TiledMap()
    val layers = genMap.getLayers()

    val tileWidth=32
    val tileHeight=32
    val layer1 = new TiledMapTileLayer(width, height, tileWidth, tileHeight)
    val dirtCentercell = new Cell()

    dirtCentercell.setTile(new StaticTiledMapTile(TestMapLoader.CenterDirtRegion))

    for{
      i<-0 to 100
      j<-0 to 100
    } {
      layer1.setCell(i, j, dirtCentercell)
     }
    layers.add(layer1)

    genMap
  }
}


object TestMapLoader{



  val texture= new Texture(Gdx.files.internal("base_out_atlas.png"))
  val dirtX=480
  val dirtY=64
  val dirtTileSize=32
  val NWDirtRegion=new TextureRegion(texture, dirtX, dirtY,dirtTileSize,dirtTileSize)

  val WDirtRegion=new TextureRegion(texture, dirtX+dirtTileSize, dirtY,dirtTileSize,dirtTileSize)

  val NEDirtRegion=new TextureRegion(texture, dirtX+2*dirtTileSize, dirtY,dirtTileSize,dirtTileSize)

  val CenterDirtRegion=new TextureRegion(texture, dirtX+dirtTileSize, dirtY+dirtTileSize,dirtTileSize,dirtTileSize)

}