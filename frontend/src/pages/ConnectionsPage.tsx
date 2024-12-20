import ConnectionsView from "@/components/features/connections/ConnectionsView";
import { useConnectionsQuery } from "@/hooks/features/connections/useConnections";

export default function Connections() {
  const connectionsQuery = useConnectionsQuery();

  if (connectionsQuery.isLoading) {
    return (
      <div className="flex flex-col items-center justify-between h-full">
        <h1>Loading...</h1>
      </div>
    );
  }

  if (connectionsQuery.isError) {
    throw new Error(connectionsQuery.error.message);
  }

  if (connectionsQuery.isSuccess) {
    return (
      <div className="flex flex-col items-center justify-between h-full">
        <ConnectionsView connections={connectionsQuery.data} />
      </div>
    );
  }
}
