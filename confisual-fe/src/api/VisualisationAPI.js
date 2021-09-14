export function VisualisationAPI() {}

function visualise(domain, input) {
  return fetch(`/api/v1/visualise/${domain}`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      xmlConfig: input
    }),
  })
  .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok: ' + response.status);
      }
      
      return response.json();
  });
}

VisualisationAPI.visualise = function (domain, input) {
  return visualise(domain, input);
}
