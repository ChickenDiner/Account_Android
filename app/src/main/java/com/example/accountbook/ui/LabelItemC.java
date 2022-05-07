/*** 分类管理界面单个分类项
 * by gw 1711324
 */
package com.example.accountbook.ui;

public class LabelItemC
{
    public String label_name;
    public  int image_id;
    public Boolean expend_or_income;//expend:true
    public LabelItemC(String label_name, Boolean expend_or_income, int image_id)
    {
        this.label_name=label_name;
        this.image_id=image_id;
        this.expend_or_income=expend_or_income;
    }
    public String getLabel_name()
    {
        return label_name;
    }
    public int getImage_id(){return image_id;}
    public Boolean getExpend_or_income()
    {
        return expend_or_income;
    }

}
