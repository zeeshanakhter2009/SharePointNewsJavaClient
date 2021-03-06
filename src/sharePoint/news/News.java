/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharePoint.news;

/**
 *
 * @author Zeeshan Akhter
 */
public class News {

    private String ID;
    private String Title;
    private String MohTitleAr;
    private String MohPicture;
    private String MohPostingDate;
    private String MohExpiryDate;
    private String MohBrief;
    private String MohBriefAr;
    private String MetaInfo;
    private String ContentType;
    private String Created;
    private String Modified;
    private String Author;
    private String Editor;
    private String LinkTitleNoMenu;
    private String LinkTitle;
    private String LinkTitle2;
    private String Priority;
    private String PushNotificationStatus;

    public News() {
    }

    public News(String ID, String Title, String MohTitleAr, String MohPicture, String MohPostingDate, String MohExpiryDate, String MohBrief, String MohBriefAr, String MetaInfo, String ContentType, String Created, String Modified, String Author, String Editor, String LinkTitleNoMenu, String LinkTitle, String LinkTitle2, String Priority, String PushNotificationStatus) {
        this.ID = ID;
        this.Title = Title;
        this.MohTitleAr = MohTitleAr;
        this.MohPicture = MohPicture;
        this.MohPostingDate = MohPostingDate;
        this.MohExpiryDate = MohExpiryDate;
        this.MohBrief = MohBrief;
        this.MohBriefAr = MohBriefAr;
        this.MetaInfo = MetaInfo;
        this.ContentType = ContentType;
        this.Created = Created;
        this.Modified = Modified;
        this.Author = Author;
        this.Editor = Editor;
        this.LinkTitleNoMenu = LinkTitleNoMenu;
        this.LinkTitle = LinkTitle;
        this.LinkTitle2 = LinkTitle2;
        this.Priority = Priority;
        this.PushNotificationStatus = PushNotificationStatus;
    }

    public String getPushNotificationStatus() {
        return PushNotificationStatus;
    }

    public void setPushNotificationStatus(String PushNotificationStatus) {
        this.PushNotificationStatus = PushNotificationStatus;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getMohTitleAr() {
        return MohTitleAr;
    }

    public void setMohTitleAr(String MohTitleAr) {
        this.MohTitleAr = MohTitleAr;
    }

    public String getMohPicture() {
        return MohPicture;
    }

    public void setMohPicture(String MohPicture) {
        this.MohPicture = MohPicture;
    }

    public String getMohPostingDate() {
        return MohPostingDate;
    }

    public void setMohPostingDate(String MohPostingDate) {
        this.MohPostingDate = MohPostingDate;
    }

    public String getMohExpiryDate() {
        return MohExpiryDate;
    }

    public void setMohExpiryDate(String MohExpiryDate) {
        this.MohExpiryDate = MohExpiryDate;
    }

    public String getMohBrief() {
        return MohBrief;
    }

    public void setMohBrief(String MohBrief) {
        this.MohBrief = MohBrief;
    }

    public String getMohBriefAr() {
        return MohBriefAr;
    }

    public void setMohBriefAr(String MohBriefAr) {
        this.MohBriefAr = MohBriefAr;
    }

    public String getMetaInfo() {
        return MetaInfo;
    }

    public void setMetaInfo(String MetaInfo) {
        this.MetaInfo = MetaInfo;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String ContentType) {
        this.ContentType = ContentType;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String Created) {
        this.Created = Created;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String Modified) {
        this.Modified = Modified;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public String getEditor() {
        return Editor;
    }

    public void setEditor(String Editor) {
        this.Editor = Editor;
    }

    public String getLinkTitleNoMenu() {
        return LinkTitleNoMenu;
    }

    public void setLinkTitleNoMenu(String LinkTitleNoMenu) {
        this.LinkTitleNoMenu = LinkTitleNoMenu;
    }

    public String getLinkTitle() {
        return LinkTitle;
    }

    public void setLinkTitle(String LinkTitle) {
        this.LinkTitle = LinkTitle;
    }

    public String getLinkTitle2() {
        return LinkTitle2;
    }

    public void setLinkTitle2(String LinkTitle2) {
        this.LinkTitle2 = LinkTitle2;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String Priority) {
        this.Priority = Priority;
    }

    @Override
    public String toString() {
        return "News{" + "ID=" + ID + ", Title=" + Title + ", MohTitleAr=" + MohTitleAr + ", MohPicture=" + MohPicture + ", MohPostingDate=" + MohPostingDate + ", MohExpiryDate=" + MohExpiryDate + ", MohBrief=" + MohBrief + ", MohBriefAr=" + MohBriefAr + ", MetaInfo=" + MetaInfo + ", ContentType=" + ContentType + ", Created=" + Created + ", Modified=" + Modified + ", Author=" + Author + ", Editor=" + Editor + ", LinkTitleNoMenu=" + LinkTitleNoMenu + ", LinkTitle=" + LinkTitle + ", LinkTitle2=" + LinkTitle2 + ", Priority=" + Priority + ", PushNotificationStatus=" + PushNotificationStatus + '}';
    }

}
