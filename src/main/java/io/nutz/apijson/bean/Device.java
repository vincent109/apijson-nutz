/**
 * 
 */
package io.nutz.apijson.bean;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import apijson.MethodAccess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * 设备表
 */
@MethodAccess
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("t_device")
public class Device {
	@Id
	@Column("id")
	@ColDefine(type = ColType.INT,notNull = true, customType = "int(8)")
	private long id;
	/**
	 * 设备编号
	 */
	@Column("dno")
	@ColDefine(type = ColType.VARCHAR,notNull = true, width = 32)
	@Comment("设备编号")
	private String dno;
	/**
	 * 信号强度
	 */
	@Column("sign")
	@ColDefine(type = ColType.INT,notNull = true, customType = "int(2)")
	@Comment("信号强度")
	private int sign;
	/**
	 * 投放地址
	 */
	@Column("address")
	@ColDefine(type = ColType.VARCHAR, width = 50)
	@Comment("投放地址")
	private String address;
	/**
	 * 省市
	 */
	@Column("prov")
	@ColDefine(type = ColType.INT,customType = "int(6)")
	@Comment("信号强度")
	private int prov;
}
