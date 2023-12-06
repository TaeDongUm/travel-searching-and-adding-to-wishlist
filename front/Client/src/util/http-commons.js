import axios from "axios";
const { VITE_VUE_API_URL } = import.meta.env;

// local vue api axios instance
export function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },

    // withCredentials : true

  });

  return instance;
}

export const auth = (instance) => {
  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('access_token') || "";
      config.headers = {

        Authorization: `Bearer ${token}` || ''
      };
      // config.withCredentials = true;

      return config;
    },
    (error) => Promise.reject(error.response)
  );
  return instance;
};
