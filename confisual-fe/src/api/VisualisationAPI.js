export function VisualisationAPI() {}

function visualise(input) {
  return fetch(process.env.REACT_APP_BACKEND_URL + '/visualise/nevisAuth', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      nevisAuthConfig: input
    }),
  })
  .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok: ' + response.status);
      }
      
      return response.json();
  });
}

VisualisationAPI.visualise = function (input) {
  return visualise(input);
}
