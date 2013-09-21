package io.tesla.sra;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.transport.FetchConnection;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.util.FileUtils;

// validate
// construct
// whether to overwrite working directory
// great error messages coming back

public class GitProvider  {

  // url
  // repository
  // branch

  private final String url;
  private final String sha1;
  private final File workingDirectory;

  public GitProvider(String url, String sha1, File workingDirectory) {
    this.url = url;
    this.sha1 = sha1;
    this.workingDirectory = workingDirectory;
  }

  public void checkout() {

    try {
      FileUtils.delete(workingDirectory, FileUtils.RECURSIVE);
    } catch (IOException e1) {
      // Do nothing
    }

    Git git = Git.cloneRepository().setURI(url).setDirectory(workingDirectory).setProgressMonitor(NullProgressMonitor.INSTANCE).call();

    try {
      git.checkout().setCreateBranch(true).setName(sha1).setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).setStartPoint(sha1).call();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void getBranches(String remote) throws Exception {

    final Transport tn = Transport.open(new FileRepository(new File(".")), remote);
    final FetchConnection c = tn.openFetch();
    try {
      for (final Ref r : c.getRefs()) {
        show(r.getObjectId(), r.getName());
        if (r.getPeeledObjectId() != null)
          show(r.getPeeledObjectId(), r.getName() + "^{}"); //$NON-NLS-1$
      }
    } finally {
      c.close();
      tn.close();
    }
  }

  private void show(final AnyObjectId id, final String name) throws IOException {
    System.out.print(id.name());
    System.out.print('\t');
    System.out.print(name);
    System.out.println();
  }

  public boolean validate(String connection) {
    return true;
  }

  public static void main(String[] args) throws Exception {
    GitProvider p = new GitProvider("https://git-wip-us.apache.org/repos/asf/maven.git", "0728685237757ffbf44136acec0402957f723d9a", new File("/tmp/repo"));
    p.checkout();
  }

}
