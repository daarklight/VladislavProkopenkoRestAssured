
package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prefs {

    @SerializedName("permissionLevel")
    @Expose
    private String permissionLevel;
    @SerializedName("hideVotes")
    @Expose
    private Boolean hideVotes;
    @SerializedName("voting")
    @Expose
    private String voting;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("invitations")
    @Expose
    private String invitations;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("backgroundColor")
    @Expose
    private String backgroundColor;

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public Boolean getHideVotes() {
        return hideVotes;
    }

    public void setHideVotes(Boolean hideVotes) {
        this.hideVotes = hideVotes;
    }

    public String getVoting() {
        return voting;
    }

    public void setVoting(String voting) {
        this.voting = voting;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getInvitations() {
        return invitations;
    }

    public void setInvitations(String invitations) {
        this.invitations = invitations;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Prefs.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this)))
          .append('[');
        sb.append("permissionLevel");
        sb.append('=');
        sb.append(((this.permissionLevel == null) ? "<null>" : this.permissionLevel));
        sb.append(',');
        sb.append("hideVotes");
        sb.append('=');
        sb.append(((this.hideVotes == null) ? "<null>" : this.hideVotes));
        sb.append(',');
        sb.append("voting");
        sb.append('=');
        sb.append(((this.voting == null) ? "<null>" : this.voting));
        sb.append(',');
        sb.append("comments");
        sb.append('=');
        sb.append(((this.comments == null) ? "<null>" : this.comments));
        sb.append(',');
        sb.append("invitations");
        sb.append('=');
        sb.append(((this.invitations == null) ? "<null>" : this.invitations));
        sb.append(',');
        sb.append("background");
        sb.append('=');
        sb.append(((this.background == null) ? "<null>" : this.background));
        sb.append(',');
        sb.append("backgroundColor");
        sb.append('=');
        sb.append(((this.backgroundColor == null) ? "<null>" : this.backgroundColor));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.permissionLevel == null) ? 0 : this.permissionLevel.hashCode()));
        result = ((result * 31) + ((this.backgroundColor == null) ? 0 : this.backgroundColor.hashCode()));
        result = ((result * 31) + ((this.comments == null) ? 0 : this.comments.hashCode()));
        result = ((result * 31) + ((this.invitations == null) ? 0 : this.invitations.hashCode()));
        result = ((result * 31) + ((this.hideVotes == null) ? 0 : this.hideVotes.hashCode()));
        result = ((result * 31) + ((this.background == null) ? 0 : this.background.hashCode()));
        result = ((result * 31) + ((this.voting == null) ? 0 : this.voting.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Prefs) == false) {
            return false;
        }
        Prefs rhs = ((Prefs) other);
        return ((((((
            ((this.permissionLevel == rhs.permissionLevel) || ((this.permissionLevel != null) && this.permissionLevel
                .equals(rhs.permissionLevel))) && ((this.backgroundColor == rhs.backgroundColor) || (
                (this.backgroundColor != null) && this.backgroundColor.equals(rhs.backgroundColor)))) && (
            (this.comments == rhs.comments) || ((this.comments != null) && this.comments.equals(rhs.comments)))) && (
            (this.invitations == rhs.invitations) || ((this.invitations != null) && this.invitations
                .equals(rhs.invitations)))) && ((this.hideVotes == rhs.hideVotes) || ((this.hideVotes != null)
            && this.hideVotes.equals(rhs.hideVotes)))) && ((this.background == rhs.background) || (
            (this.background != null) && this.background.equals(rhs.background)))) && ((this.voting == rhs.voting) || (
            (this.voting != null) && this.voting.equals(rhs.voting))));
    }
}
