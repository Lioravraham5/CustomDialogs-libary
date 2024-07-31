package com.example.customdialogs.RegisterDialog;

public class RegistrationOption {
    private SupportedRegisterOptions.AllSupportedOption optionType;
    private int iconResId;
    private String hint;

    public RegistrationOption(SupportedRegisterOptions.AllSupportedOption optionType, int iconResId, String hint) {
        this.optionType = optionType;
        this.iconResId = iconResId;
        this.hint = hint;
    }

    public SupportedRegisterOptions.AllSupportedOption getOptionType() {
        return optionType;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getHint() {
        return hint;
    }

    public void setOptionType(SupportedRegisterOptions.AllSupportedOption optionType) {
        this.optionType = optionType;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
