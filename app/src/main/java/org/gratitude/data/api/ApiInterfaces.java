package org.gratitude.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by paoloc on 26/01/17.
 */

public interface ApiInterfaces {

    // In case you don’t want to pass optional param with the request, just pass null

    @GET("projectservice/featured/projects")
    Call<> getFeaturedProjects();

    @GET("projectservice/themes")
    Call<> getThemes();

    @GET("projectservice/themes/{theme}/projects")
    Call<> getAllProjectsForTheme(@Path("theme") String theme, @Query("nextProjectId") int nextProjectId);

    @GET("orgservice/all/organizations")
    Call<> getOrganizations(@Query("nextOrgId") int nextOrgId);

    @GET("projectservice/organizations/{orgId}/projects")
    Call<> getAllProjectsForOrganization(@Path("orgId") String theme, @Query("nextProjectId") int nextProjectId);

    @GET("orgservice/organization/bridge/{bridgeId}")
    Call<> getOrganizationByBridgeId(@Path("bridgeId") String bridgeId);

    @GET("projectservice/all/projects")
    Call<> getAllProjects(@Query("nextProjectId") int nextProjectId);

    @GET("projectservice/projects/{projectId}")
    Call<> getProject(@Path("projectId") int projectId);

    @GET("projectservice/projects/{projectId}/reports")
    Call<> getProjectReport(@Path("projectId") int projectId);
}
