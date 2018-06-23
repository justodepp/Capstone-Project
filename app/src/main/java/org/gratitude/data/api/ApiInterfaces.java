package org.gratitude.data.api;

import org.gratitude.data.model.report.Report;
import org.gratitude.data.model.response.AllOrganizations;
import org.gratitude.data.model.response.AllProjects;
import org.gratitude.data.model.response.FeaturedProjects;
import org.gratitude.data.model.response.OrganizationByBridgeId;
import org.gratitude.data.model.response.ProjectById;
import org.gratitude.data.model.response.ProjectByOrganization;
import org.gratitude.data.model.response.ProjectByTheme;
import org.gratitude.data.model.response.AllThemes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterfaces {

    // In case you don’t want to pass optional param with the request, just pass null

    @GET("projectservice/featured/projects")
    Call<FeaturedProjects> getFeaturedProjects();

    @GET("projectservice/themes")
    Call<AllThemes> getThemes();

    @GET("projectservice/themes/{theme}/projects/active")
    Call<ProjectByTheme> getAllProjectsForTheme(@Path("theme") String theme, @Query("nextProjectId") Long nextProjectId);

    @GET("orgservice/all/organizations/active")
    Call<AllOrganizations> getOrganizations(@Query("nextOrgId") Long nextOrgId);

    @GET("projectservice/organizations/{orgId}/projects")
    Call<ProjectByOrganization> getAllProjectsForOrganization(@Path("orgId") String theme, @Query("nextProjectId") Long nextProjectId);

    @GET("orgservice/organization/bridge/{bridgeId}")
    Call<OrganizationByBridgeId> getOrganizationByBridgeId(@Path("bridgeId") String bridgeId);

    @GET("projectservice/all/projects/active")
    Call<AllProjects> getAllProjects(@Query("nextProjectId") Long nextProjectId);

    @GET("projectservice/projects/{projectId}")
    Call<ProjectById> getProject(@Path("projectId") int projectId);

    @GET("projectservice/projects/{projectId}/reports")
    Call<Report> getProjectReport(@Path("projectId") int projectId);
}
