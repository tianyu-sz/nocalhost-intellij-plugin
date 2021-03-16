package dev.nocalhost.plugin.intellij.ui.action.workload;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

import dev.nocalhost.plugin.intellij.settings.NocalhostRepo;
import dev.nocalhost.plugin.intellij.settings.NocalhostSettings;
import dev.nocalhost.plugin.intellij.ui.tree.node.ResourceNode;
import dev.nocalhost.plugin.intellij.utils.FileChooseUtil;

public class AssociateLocalDirectoryAction extends AnAction {

    private final Project project;
    private final ResourceNode node;

    public AssociateLocalDirectoryAction(Project project, ResourceNode resourceNode) {
        super("Associate Local Directory");

        this.project = project;
        this.node = resourceNode;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final NocalhostSettings nocalhostSettings = ServiceManager.getService(NocalhostSettings.class);

        final Path parentDir = FileChooseUtil.chooseSingleDirectory(project);
        System.out.println(parentDir);

        if (parentDir == null || StringUtils.isBlank(parentDir.toString())) {
            return;
        }

        final Optional<NocalhostRepo> repo = nocalhostSettings.getRepos().stream()
                                                                       .filter(repos -> repos.getHost().equals(nocalhostSettings.getBaseUrl())
                                                                               && repos.getEmail().equals(nocalhostSettings.getUserInfo().getEmail())
                                                                               && repos.getAppName().equals(node.devSpace().getContext().getApplicationName())
                                                                               && repos.getDeploymentName().equals(node.resourceName())).findFirst();

        NocalhostRepo nocalhostRepo;
        if (repo.isPresent()) {
            nocalhostRepo = repo.get();
            nocalhostSettings.removeRepos(nocalhostRepo);
            nocalhostRepo.setRepoPath(parentDir.toString());
        } else {
            nocalhostRepo = new NocalhostRepo(nocalhostSettings.getBaseUrl(), nocalhostSettings.getUserInfo().getEmail(), node.devSpace().getContext().getApplicationName(), node.resourceName(), parentDir.toString());
        }
        nocalhostSettings.addRepos(nocalhostRepo);
    }
}
