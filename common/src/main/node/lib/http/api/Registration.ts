import axios from "axios";
import { axiosInterceptor } from "../../../utils/Date";
import { Registration } from "../../../@types/Registration";

export enum ApiEndpoint {
  GetAllRegistrations = "/registration",
  GetRegistrationById = "/registration/",
  CreateRegistration = "/registration",
  UpdateRegistration = "/registration/",
  DeleteRegistrationById = "/registration/",
}

axios.interceptors.response.use(axiosInterceptor);

/**
 * Create a new registration
 * @param apiUrl Base URL of the API (e.g. https://api.example.com)
 * @param registration Registration object
 * @param accessToken Access token for the API (e.g. JWT)
 */
export const createRegistration = async (
  apiUrl: string,
  registration: Registration,
  accessToken: string,
) => {
  const response = await axios.post(
    apiUrl + ApiEndpoint.CreateRegistration,
    registration,
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  if (response.status !== 201) {
    throw new Error(response.status + " : " + response.statusText);
  }

  return response;
};

/**
 * Get registration by ID
 * @param apiUrl Base URL of the API (e.g. https://api.example.com)
 * @param id Registration ID
 * @param accessToken Access token for the API (e.g. JWT)
 */
export const getRegistrationById = async (
  apiUrl: string,
  id: string,
  accessToken: string,
) => {
  const response = await axios.get(
    apiUrl + ApiEndpoint.GetRegistrationById + id,
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  if (response.status !== 200) {
    throw new Error(response.status + " : " + response.statusText);
  }

  return response;
};

/**
 * Delete registration by ID
 * @param apiUrl Base URL of the API (e.g. https://api.example.com)
 * @param id Registration ID
 * @param accessToken Access token for the API (e.g. JWT)
 */
export const deleteRegistrationById = async (
  apiUrl: string,
  id: string,
  accessToken: string,
) => {
  const response = await axios.delete(
    apiUrl + ApiEndpoint.DeleteRegistrationById + id,
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  if (response.status !== 204) {
    throw new Error(response.status + " : " + response.statusText);
  }

  return response;
};

/**
 * Update registration by ID
 * @param apiUrl Base URL of the API (e.g. https://api.example.com)
 * @param id Registration ID
 * @param registration Registration object
 * @param accessToken Access token for the API (e.g. JWT)
 */
export const updateRegistration = async (
  apiUrl: string,
  id: string,
  registration: Registration,
  accessToken: string,
) => {
  const response = await axios.put(
    apiUrl + ApiEndpoint.UpdateRegistration + id,
    registration,
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );

  if (response.status !== 200) {
    throw new Error(response.status + " : " + response.statusText);
  }

  return response;
};
