package net.impacthub.app.presenter.features.home;

import net.impacthub.app.model.vo.contacts.ContactVO;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.base.UiContract;

/**
 * Created by filippo on 27/11/2017.
 */

public interface MainTabsUiContract extends UiContract {

    void onError(Throwable throwable);

    void onLoadProject(ProjectVO projectVO);

    void onLoadGroup(GroupVO groupVO);

    void onLoadContact(ContactVO contactVO);

    void onLoadMember(MemberVO memberVO);
}
