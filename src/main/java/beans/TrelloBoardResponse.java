package beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrelloBoardResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("descData")
    @Expose
    private Object descData;
    @SerializedName("closed")
    @Expose
    private Boolean closed;
    @SerializedName("idOrganization")
    @Expose
    private String idOrganization;
    @SerializedName("idEnterprise")
    @Expose
    private Object idEnterprise;
    @SerializedName("pinned")
    @Expose
    private Boolean pinned;
    @SerializedName("prefs")
    @Expose
    private Prefs prefs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getDescData() {
        return descData;
    }

    public void setDescData(Object descData) {
        this.descData = descData;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(String idOrganization) {
        this.idOrganization = idOrganization;
    }

    public Object getIdEnterprise() {
        return idEnterprise;
    }

    public void setIdEnterprise(Object idEnterprise) {
        this.idEnterprise = idEnterprise;
    }

    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

    public Prefs getPrefs() {
        return prefs;
    }

    public void setPrefs(Prefs prefs) {
        this.prefs = prefs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TrelloBoardResponse.class.getName()).append('@')
          .append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("desc");
        sb.append('=');
        sb.append(((this.desc == null) ? "<null>" : this.desc));
        sb.append(',');
        sb.append("descData");
        sb.append('=');
        sb.append(((this.descData == null) ? "<null>" : this.descData));
        sb.append(',');
        sb.append("closed");
        sb.append('=');
        sb.append(((this.closed == null) ? "<null>" : this.closed));
        sb.append(',');
        sb.append("idOrganization");
        sb.append('=');
        sb.append(((this.idOrganization == null) ? "<null>" : this.idOrganization));
        sb.append(',');
        sb.append("idEnterprise");
        sb.append('=');
        sb.append(((this.idEnterprise == null) ? "<null>" : this.idEnterprise));
        sb.append(',');
        sb.append("pinned");
        sb.append('=');
        sb.append(((this.pinned == null) ? "<null>" : this.pinned));
        sb.append(',');
        sb.append("prefs");
        sb.append('=');
        sb.append(((this.prefs == null) ? "<null>" : this.prefs));
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
        result = ((result * 31) + ((this.descData == null) ? 0 : this.descData.hashCode()));
        result = ((result * 31) + ((this.pinned == null) ? 0 : this.pinned.hashCode()));
        result = ((result * 31) + ((this.idEnterprise == null) ? 0 : this.idEnterprise.hashCode()));
        result = ((result * 31) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((result * 31) + ((this.idOrganization == null) ? 0 : this.idOrganization.hashCode()));
        result = ((result * 31) + ((this.closed == null) ? 0 : this.closed.hashCode()));
        result = ((result * 31) + ((this.id == null) ? 0 : this.id.hashCode()));
        result = ((result * 31) + ((this.desc == null) ? 0 : this.desc.hashCode()));
        result = ((result * 31) + ((this.prefs == null) ? 0 : this.prefs.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TrelloBoardResponse) == false) {
            return false;
        }
        TrelloBoardResponse rhs = ((TrelloBoardResponse) other);
        return ((((((
            ((((this.descData == rhs.descData) || ((this.descData != null) && this.descData.equals(rhs.descData))) && (
                (this.pinned == rhs.pinned) || ((this.pinned != null) && this.pinned.equals(rhs.pinned)))) && (
                (this.idEnterprise == rhs.idEnterprise) || ((this.idEnterprise != null) && this.idEnterprise
                    .equals(rhs.idEnterprise)))) && ((this.name == rhs.name) || ((this.name != null) && this.name
                .equals(rhs.name)))) && ((this.idOrganization == rhs.idOrganization) || ((this.idOrganization != null)
            && this.idOrganization.equals(rhs.idOrganization)))) && ((this.closed == rhs.closed) || (
            (this.closed != null) && this.closed.equals(rhs.closed)))) && ((this.id == rhs.id) || ((this.id != null)
            && this.id.equals(rhs.id)))) && ((this.desc == rhs.desc) || ((this.desc != null) && this.desc
            .equals(rhs.desc)))) && ((this.prefs == rhs.prefs) || ((this.prefs != null) && this.prefs
            .equals(rhs.prefs))));
    }
}

