package dev.nocalhost.plugin.intellij.commands.data.kuberesource;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Selector {
    private Map<String, String> matchLabels;
}
