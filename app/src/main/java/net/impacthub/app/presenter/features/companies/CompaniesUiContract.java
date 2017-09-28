/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.app.presenter.features.companies;

import net.impacthub.app.model.vo.companies.CompanyVO;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public interface CompaniesUiContract extends ErrorHandlerUiContract {

    void onLoadCompanies(List<CompanyVO> companies);

    void onShowTick(Map<String, List<String>> filters);

    void onHideTick();
}
