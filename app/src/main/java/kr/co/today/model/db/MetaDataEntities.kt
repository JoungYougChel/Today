package kr.co.today.model.db

import androidx.room.*

@Entity(tableName = "TB_META")
data class MetaDataEntities (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "idx") val idx: Int?,
    @ColumnInfo(name = "meta_code") var metaCode: String,
    @ColumnInfo(name = "meta_data") var metaData: String
)

@Dao
interface RoomMetaData{
    @Query("SELECT * FROM TB_META")
    fun getAll(): List<MetaDataEntities>

    @Query("SELECT * FROM TB_META WHERE meta_code = :metaCode LIMIT 1")
    fun selectMeta(metaCode: String): MetaDataEntities

    @Insert
    fun insertMeta(meta: MetaDataEntities)

    @Query("UPDATE TB_META SET meta_data = :metaData WHERE meta_code = :metaCode")
    fun updateMeta(metaCode: String,metaData: String)

    @Query("DELETE FROM TB_META WHERE meta_code = :metaCode")
    fun deleteMeta(metaCode:String)

    @Query("DELETE FROM TB_META")
    fun deleteMetaAll()

}