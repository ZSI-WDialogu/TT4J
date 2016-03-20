package TT4J.activeLink;

import java.util.List;

public class ActiveLinkData {
    public static final String ACTIVE_LINK_DATE_FORMAT = "dd.MM.yyyy HH:mm";
    private String nick;
    private String userName;
    private int channelId;
    private String agenda;
    private int expertChannelId;
    private Boolean isExpert;
    private Boolean debateWithExpertsPanel;
    private String startExpertsPanelDate;
    private String endExpertsPanelDate;
    private String startDate;
    private String endDate;
    private List<String> expertLogins;
    private String expertPanelModeratorLogin;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }


    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public int getExpertChannelId() {
        return expertChannelId;
    }

    public void setExpertChannelId(int expertChannelId) {
        this.expertChannelId = expertChannelId;
    }

    public void setIsExpert(boolean isExpert) {
        this.isExpert = isExpert;
    }

    public Boolean isExpert() {
        return isExpert;
    }

    public void setExpert(Boolean isExpert) {
        this.isExpert = isExpert;
    }

    public void setDebateWithExpertsPanel(Boolean debateWithExpertsPanel) {
        this.debateWithExpertsPanel = debateWithExpertsPanel;
    }

    public Boolean getDebateWithExpertsPanel() {
        return debateWithExpertsPanel;
    }

    public void setStartExpertsPanelDate(String startExpertsPanelDate) {
        this.startExpertsPanelDate = startExpertsPanelDate;
    }

    public String getStartExpertsPanelDate() {
        return startExpertsPanelDate;
    }

    public void setEndExpertsPanelDate(String endExpertsPanelDate) {
        this.endExpertsPanelDate = endExpertsPanelDate;
    }

    public String getEndExpertsPanelDate() {
        return endExpertsPanelDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setExpertLogins(List<String> expertLogins) {
        this.expertLogins = expertLogins;
    }

    public List<String> getExpertLogins() {
        return expertLogins;
    }

    public void setExpertPanelModeratorLogin(String expertPanelModeratorLogin) {
        this.expertPanelModeratorLogin = expertPanelModeratorLogin;
    }

    public String getExpertPanelModeratorLogin() {
        return expertPanelModeratorLogin;
    }
}
